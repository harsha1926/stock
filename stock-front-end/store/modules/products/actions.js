import { fetchProducts, postProduct, putProduct } from '~/api/products'

const getProducts = context => {
  fetchProducts().then(response => {
    context.commit('PRODUCTS_LIST_FETCHED', response.data)
  })
}

const addNewProduct = (context, payload) => {
  return new Promise(function(resolve, reject) {
    postProduct(payload)
      .then(response => {
        if (response.data) {
          context.commit('ADDED_PRODUCT', response.data)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

const updateProduct = (context, payload) => {
  return new Promise(function(resolve, reject) {
    putProduct(payload)
      .then(response => {
        if (response.data) {
          context.commit('UPDATED_PRODUCT', response.data)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

export default {
  getProducts,
  addNewProduct,
  updateProduct
}
