import axios from 'axios'

const fetchSuppliers = () => {
  return axios.get(`/api/suppliers`)
}

const fetchSupplier = id => {
  return axios.get(`/api/suppliers/${id}`)
}

const postSupplier = payload => {
  return axios.post(`/api/suppliers`, payload)
}

const putSupplier = payload => {
  return axios.put(`/api/suppliers/${payload.id}`, payload)
}

const deleteSupplier = id => {
  return axios.delete(`/api/suppliers/${id}`)
}

export {
  fetchSuppliers,
  fetchSupplier,
  postSupplier,
  putSupplier,
  deleteSupplier
}
