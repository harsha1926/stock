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

export { getCustomers, getCustomer, postCustomer }
