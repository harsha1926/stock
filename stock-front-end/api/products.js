import axios from 'axios'

const getProducts = () => {
  return axios.get(`/api/products`)
}

const getProduct = id => {
  return axios.get(`/api/products/${id}`)
}

export { getProducts, getProduct }
