
export const saveToken = (token) => {
    document.cookie = `auth=${token}`;
    localStorage.setItem('token', token);
  };

export const getToken = () => {
    return localStorage.getItem('token');
  };
