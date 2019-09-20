import axios from 'axios'

const getCustomers = () => {
  return axios.get(`/api/customers`)
}

const getCustomer = id => {
  return axios.get(`/api/customers/${id}`)
}

export { getCustomers, getCustomer }
