import axios from 'axios'

const fetchStockParcels = () => {
  return axios.get(`/api/stockParcel`)
}

const fetchStockParcel = id => {
  return axios.get(`/api/stockParcel/${id}`)
}
const postStockParcel = payload => {
  return axios.post(`/api/stockParcel`, payload)
}
const deleteStockParcel = id => {
  return axios.delete(`/api/stockParcel/${id}`)
}
const putStockParcel = payload => {
  return axios.put(`/api/stockParcel/${payload.id}`, payload)
}

export {
  fetchStockParcels,
  fetchStockParcel,
  postStockParcel,
  deleteStockParcel,
  putStockParcel
}
