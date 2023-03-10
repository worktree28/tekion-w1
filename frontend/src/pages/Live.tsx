import { useEffect, useState } from 'react';
import Card from '../util/Card';
import SockJsClient from 'react-stomp';
import { Client } from '@stomp/stompjs';

const SOCKET_URL = 'ws://localhost:8080/ws-message';

export default function Live() {
  const [matches, setMatches] = useState<any[]>([]);
  const [connected, isConnected] = useState(false);
  let onConnected = () => {
    console.log('Connected!!');
    client.subscribe('/topic/match', function (msg) {
      if (msg.body) {
        var jsonBody = JSON.parse(msg.body);
        jsonBody.new = true;
        console.log(jsonBody.id);
        if (
          matches.find((match: any) => match.id === jsonBody.id) === undefined
        ) {
          console.log(jsonBody);
          setMatches((prev) => [...prev, jsonBody]);
        }
      }
    });
  };
  let onDisconnected = () => {
    console.log('Disconnected!!');
  };

  const client = new Client({
    brokerURL: SOCKET_URL,
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
    onConnect: onConnected,
    onDisconnect: onDisconnected,
  });

  useEffect(() => {
    // let client = new WebSocket('ws://localhost:8080/ws-message');
    // client.onopen = () => {
    //   console.log('WebSocket Client Connected');
    // };
    // client.addEventListener('message', (event) => {
    //   console.log('Message from server ', event.data);
    // });
    // console.log(client);

    fetch('http://localhost:8080/all', {
      method: 'GET',
      headers: {
        'Access-Control-Allow-Origin': '*',
      },
    })
      .then((res) => res.json())
      .then((data) => setMatches(data.content));
  }, []);
  let matchList = matches.map((match: any) => {
    return <Card key={match.id} match={match} />;
  });
  const connect = () => {
    if (!connected) {
      client.activate();
      isConnected(true);
    }
  };
  const disconnected = () => {
    if (connected) {
      client.deactivate();
      isConnected(false);
    }
  };

  return (
    <>
      <button onClick={() => connect()}>Connect</button>
      <button onClick={() => disconnected()}>Disconnect</button>
      {matchList}
    </>
  );
}
