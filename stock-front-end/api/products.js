import axios from 'axios'

const getProducts = () => {
  return axios.get(`/api/products`)
}

const getProduct = id => {
  return axios.get(`/api/products/${id}`)
}
const postProduct = payload => {
  return axios.post(`/api/products`, payload)
}
const deleteProduct = id => {
  return axios.delete(`/api/suppliers/${id}`)
}

export { getProducts, getProduct, postProduct, deleteProduct }
