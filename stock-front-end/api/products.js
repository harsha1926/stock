import axios from 'axios'

const fetchProducts = () => {
  return axios.get(`/api/products`)
}

const getProduct = id => {
  return axios.get(`/api/products/${id}`)
}
const postProduct = payload => {
  return axios.post(`/api/products`, payload)
}
const deleteProduct = id => {
  return axios.delete(`/api/products/${id}`)
}
const putProduct = payload => {
  return axios.put(`/api/products/${payload.id}`, payload)
}

export { fetchProducts, getProduct, postProduct, deleteProduct, putProduct }
