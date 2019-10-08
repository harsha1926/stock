import {
  fetchStockParcels,
  fetchStockParcel,
  postStockParcel,
  deleteStockParcel,
  putStockParcel
} from '~/api/stockParcel'

const getStockParcels = context => {
  fetchStockParcels().then(response => {
    context.commit('STOCKPARCELS_LIST_FETCHED', response.data)
  })
}

const getStockParcel = (context, id) => {
  fetchStockParcel(id).then(response => {
    context.commit('STOCKPARCEL_FETCHED', response.data)
  })
}

const createStockParcel = (context, payload) => {
  return new Promise(function(resolve, reject) {
    postStockParcel(payload)
      .then(response => {
        if (response.data) {
          context.commit('ADDED_STOCKPARCEL', response.data)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

const updateStockParcel = (context, payload) => {
  return new Promise(function(resolve, reject) {
    putStockParcel(payload)
      .then(response => {
        if (response.data) {
          context.commit('UPDATED_STOCKPARCEL', payload)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

const removeStockParcel = (context, id) => {
  return new Promise(function(resolve, reject) {
    deleteStockParcel(id)
      .then(response => {
        if (response.data) {
          context.commit('DELETED_STOCKPARCEL', id)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

export default {
  getStockParcels,
  getStockParcel,
  createStockParcel,
  updateStockParcel,
  removeStockParcel
}
