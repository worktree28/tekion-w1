//create navigation bar with home link pointing towards /

import { Link } from 'react-router-dom';
import Live from './pages/Live';
import StartMatch from './pages/StartMatch';

export default function Nav() {
  return (
    <nav
      className='nav'
      style={{
        position: 'fixed',
        top: 0,
        left: 0,
        display: 'flex',
        padding: '1rem',
        marginBottom: '5rem',
        width: '100%',
      }}
    >
      <li style={{ listStyle: 'none', paddingRight: '1rem' }}>
        <Link to={`/`}>Home</Link>
      </li>
      <li style={{ listStyle: 'none', paddingRight: '1rem' }}>
        <Link to={`/playmatch`}>Start Match</Link>
      </li>
      <li style={{ listStyle: 'none', paddingRight: '1rem' }}>
        <Link to={`/live`}> Live </Link>
      </li>
      <li style={{ listStyle: 'none', paddingRight: '1rem' }}>
        <Link to={`/delete`}> Delete </Link>
      </li>
    </nav>
  );
}
