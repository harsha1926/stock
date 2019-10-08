import Vuex from 'vuex'
import appModule from './modules/app'
import suppliersModule from './modules/suppliers'
import customersModule from './modules/customers'
import productsModule from './modules/products'
import stockParcelsModule from './modules/stockParcels'

const createStore = () => {
  return new Vuex.Store({
    namespaced: true,
    modules: {
      app: appModule,
      suppliers: suppliersModule,
      customers: customersModule,
      products: productsModule,
      stockParcels: stockParcelsModule
    }
  })
}

export default createStore
