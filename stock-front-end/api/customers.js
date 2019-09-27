import axios from 'axios'

const getCustomers = () => {
  return axios.get(`/api/customers`)
}

const getCustomer = id => {
  return axios.get(`/api/customers/${id}`)
}
const postCustomer = payload => {
  return axios.post(`/api/customers`, payload)
}
const deleteCustomer = id => {
  return axios.delete(`/api/suppliers/${id}`)
}
const putCustomer = payload => {
  return axios.put(`/api/customers/${payload.id}`, payload)
}

export { getCustomers, getCustomer, postCustomer, deleteCustomer, putCustomer }
