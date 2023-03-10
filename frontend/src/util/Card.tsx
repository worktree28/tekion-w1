import './Card.css';

export default function Card(props: any) {
  props = props.match;
  return (
    <div className='card'>
      <h3>
        {props.new ? '🔴' : ''}
        {props.team1.name} vs {props.team2.name}
      </h3>
      <p>Target set: {props.targetScore}</p>
      <p>Result {props.result}</p>
    </div>
  );
}
