export default function StartMatch() {
  const startMatch = (e: any) => {
    e.preventDefault();
    fetch('http://localhost:8080/play-match', {
      method: 'GET',
      headers: {
        'Access-Control-Allow-Origin': '*',
      },
    })
      .then((res) => res.json())
      .then((data) => console.log(data));
  };
  return (
    <div>
      <button onClick={startMatch}>Start Match</button>
    </div>
  );
}
