import axios from 'axios'

const fetchCustomers = () => {
  return axios.get(`/api/customers`)
}

const fetchCustomer = id => {
  return axios.get(`/api/customers/${id}`)
}
const postCustomer = payload => {
  return axios.post(`/api/customers`, payload)
}
const deleteCustomer = id => {
  return axios.delete(`/api/customers/${id}`)
}
const putCustomer = payload => {
  return axios.put(`/api/customers/${payload.id}`, payload)
}

export {
  fetchCustomers,
  fetchCustomer,
  postCustomer,
  deleteCustomer,
  putCustomer
}
