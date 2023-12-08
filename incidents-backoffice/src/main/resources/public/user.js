
export const saveUser = (user) => {
  document.cookie = `auth=${user.token}`;
  localStorage.setItem('token', user.token);
  localStorage.setItem('userRole', user.role);
  localStorage.setItem('userEmail', user.email);
};

export const removeToken = () => {
  document.cookie = '';
  localStorage.removeItem('token');
};

export const getToken = () => {
  return localStorage.getItem('token');
};

export const getUserRole = () => {
  return localStorage.getItem('userRole');
};

export const getUserEmail = () => {
  return localStorage.getItem('userEmail');
};

export const setUser = () => {
  const role = getUserRole();
  const email = getUserEmail();
  if (role && email) {
      const auth = document.getElementById('auth');
      auth.innerHTML = '';
  
      const identification = document.createElement('a');
      identification.classList.add('btn');
      identification.classList.add('me-2');
      identification.classList.add('btn-outline-light');
      identification.type = "button";
      const newContent = document.createTextNode(`${email}/${role}`);
      identification.appendChild(newContent);
  
      auth.appendChild(identification);
  
      // remove upload csv option
      if (role !== 'OPERATOR') {
          document.getElementById('cargar-csv').remove();
      }
  }
};
