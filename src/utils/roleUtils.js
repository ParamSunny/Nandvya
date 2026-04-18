export const getRoles = () => {
  const roles = JSON.parse(localStorage.getItem("roles")) || [];

  // remove ROLE_ prefix
  return roles.map(r => r.replace("ROLE_", ""));
};

export const hasRole = (role) => {
  return getRoles().includes(role);
};

export const isAdmin = () => {
  return hasRole("ADMIN");
};
