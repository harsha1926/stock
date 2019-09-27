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
const deleteSupplier = id => {
  return axios.delete(`/api/suppliers/${id}`)
}
const putSupplier = id => {
  return axios.put(`/api/suppliers/${id}`, payload)
}

export { getSuppliers, getSupplier, postSupplier, deleteSupplier, putSupplier }
