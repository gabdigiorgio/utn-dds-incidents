
export const saveToken = (token) => {
    document.cookie = `auth=${token}`;
    localStorage.setItem('token', token);
  };

export const removeToken = () => {
    localStorage.removeItem('token');
};

export const getToken = () => {
    return localStorage.getItem('token');
  };
