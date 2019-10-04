import axios from 'axios'

const fetchProducts = () => {
  return axios.get(`/api/products`)
}

const fetchProduct = id => {
  return axios.get(`/api/products/${id}`)
}
const postProduct = payload => {
  return axios.post(`/api/products`, payload)
}
const putProduct = payload => {
  return axios.put(`/api/products/${payload.id}`, payload)
}
const deleteProduct = id => {
  return axios.delete(`/api/products/${id}`)
}

export { fetchProducts, fetchProduct, postProduct, putProduct, deleteProduct }
