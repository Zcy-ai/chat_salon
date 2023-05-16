import { useLocation } from 'react-router-dom';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Admin() {
    
    // 下个页面获取state.
    let location = useLocation();
    let {userList} = location.state;
    const navigate = useNavigate();

    // 删除用户
  const deleteUser = (user_id) => {
    if (window.confirm('Are you sure to delete this user?')) {
        axios
        .delete(`http://localhost:8080/deleteUser/${user_id}`)
        .then((response) => {
            if (response.status === 200) {
                userList = response.data.userList;
                // 刷新页面
                // window.location.reload();
                navigate("/admin", { state: { userList: response.data.userList } });
                console.log(response);
            }
        })
        .catch((error) => {
            console.log(error);
        });
    }
  };

  return (
    <div className="container">
      <h1>User List</h1>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>ID</th>
            <th>Last Name</th>
            <th>First Name</th>
            <th>Login</th>
            <th>Gender</th>
            <th>Admin</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {userList.map(user => (
            <tr key={user.id}>
              <td className="fw-bold">{user.id}</td>
              <td className="text-primary">{user.lastName}</td>
              <td className="text-primary">{user.firstName}</td>
              <td className="text-info">{user.login}</td>
              <td className="text-secondary">{user.gender}</td>
              <td className="text-muted">{user.admin === 1 ? 'Yes' : 'No'}</td>
              <td>
                <a href={`http://localhost:8080/edit`} className="btn btn-sm btn-primary me-2">Edit</a>
                <button type="button" className="btn btn-sm btn-danger" onClick={() => deleteUser(user.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <a href="/register" className="btn btn-success">Add User</a>
    </div>
  );

}

export default Admin;