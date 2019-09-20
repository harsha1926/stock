import axios from 'axios'

const getStocks = () => {
  return axios.get(`/api/stock`)
}

const getStock = id => {
  return axios.get(`/api/stock/${id}`)
}

export { getStocks, getStock }
