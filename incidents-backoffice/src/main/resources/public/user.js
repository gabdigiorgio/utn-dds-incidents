
export const saveUser = (user) => {
  document.cookie = `auth=${user.token}; path=/;`;
  localStorage.setItem('token', user.token);
  localStorage.setItem('userId', user.id);
  localStorage.setItem('userRole', user.role);
  localStorage.setItem('userEmail', user.email);
};

export const removeUser = () => {
  document.cookie = 'auth=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
  localStorage.removeItem('token');
  localStorage.removeItem('userId');
  localStorage.removeItem('userRole');
  localStorage.removeItem('userEmail');
};

export const getUserId = () => {
  return localStorage.getItem('userId');
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

  let translatedRole;

  if (role === 'USER') {
    translatedRole = 'Usuario';
  } else if (role === 'OPERATOR') {
    translatedRole = 'Operador';
  } else {
    translatedRole = 'Desconocido';
  }

  if (role && email) {
      const auth = document.getElementById('auth');
      auth.innerHTML = '';
  
      const identification = document.createElement('a');
      identification.classList.add('btn');
      identification.classList.add('me-2');
      identification.classList.add('btn-outline-light');
      identification.type = "button";
      const newContent = document.createTextNode(`${email}(${translatedRole})`);
      identification.appendChild(newContent);
  
      auth.appendChild(identification);
  
      // remove upload csv option
      if (role !== 'OPERATOR') {
          document.getElementById('cargar-csv').remove();
      }

      const logoutButton = document.createElement('button');
        logoutButton.classList.add('btn', 'btn-outline-light', 'btn-danger', 'me-2');
        logoutButton.textContent = 'Cerrar Sesión';
        logoutButton.addEventListener('click', () => {
            removeUser();
            window.location.href = '/ui/incidents';
        });
        auth.appendChild(logoutButton);
  }
};
