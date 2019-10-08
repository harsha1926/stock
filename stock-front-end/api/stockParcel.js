import axios from 'axios'

const fetchStockParcels = () => {
  return axios.get(`/api/stockParcels`)
}

const fetchStockParcel = id => {
  return axios.get(`/api/stockParcels/${id}`)
}
const postStockParcel = payload => {
  return axios.post(`/api/stockParcels`, payload)
}
const deleteStockParcel = id => {
  return axios.delete(`/api/stockParcels/${id}`)
}
const putStockParcel = payload => {
  return axios.put(`/api/stockParcels/${payload.id}`, payload)
}

export {
  fetchStockParcels,
  fetchStockParcel,
  postStockParcel,
  deleteStockParcel,
  putStockParcel
}
