// import { useState, useEffect } from 'react';
// import axios from 'axios';

// function Admin() {
//   const [users, setUsers] = useState([]);

//   useEffect(() => {
//     axios.get('/admin')
//       .then(response => setUsers(response.data))
//       .catch(error => console.error(error));
//   }, []);

//   return (
//     <div className="container">
//       <h1>User List</h1>
//       <table className="table table-striped">
//         <thead>
//           <tr>
//             <th>ID</th>
//             <th>Last Name</th>
//             <th>First Name</th>
//             <th>Login</th>
//             <th>Gender</th>
//             <th>Admin</th>
//             <th>Actions</th>
//           </tr>
//         </thead>
//         <tbody>
//           {users.map(user => (
//             <tr key={user.id}>
//               <td className="fw-bold">{user.id}</td>
//               <td className="text-primary">{user.lastName}</td>
//               <td className="text-primary">{user.firstName}</td>
//               <td className="text-info">{user.login}</td>
//               <td className="text-secondary">{user.gender}</td>
//               <td className="text-muted">{user.admin === 1 ? 'Yes' : 'No'}</td>
//               <td>
//                 <a href={`/users/edit/${user.id}`} className="btn btn-sm btn-primary me-2">Edit</a>
//                 <button type="button" className="btn btn-sm btn-danger" onClick={() => deleteUser(user.id)} >Delete</button>
//               </td>
//             </tr>
//           ))}
//         </tbody>
//       </table>
//       <a href="/register" className="btn btn-success">Add User</a>
//     </div>
//   );

//   function deleteUser(id) {
//     if (window.confirm('Are you sure to delete this user?')) {
//       axios.post(`http://localhost:8080/deleteUser/${id}`)
//         .then(() => setUsers(users.filter(user => user.id !== id)))
//         .catch(error => console.error(error));
//     }
//   }
// }

// export default Admin;
