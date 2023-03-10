export default function Delete() {
  const deleteMatch = (e: any) => {
    e.preventDefault();
    fetch('http://localhost:8080/del', {
      method: 'DELETE',
      headers: {
        'Access-Control-Allow-Origin': '*',
      },
    })
      .then((res) => res.status)
      .then((status) => console.log(status));
  };
  return (
    <div>
      <button onClick={deleteMatch}>Delete Match</button>
    </div>
  );
}
