import axios from 'axios'

const getSuppliers = () => {
  return axios.get(`/api/suppliers`)
}

const getSupplier = id => {
  return axios.get(`/api/suppliers/${id}`)
}

const postSupplier = payload => {
  return axios.post(`/api/suppliers`, payload)
}

export { getSuppliers, getSupplier, postSupplier }
