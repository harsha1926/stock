import Vuex from 'vuex'
import suppliersModule from './modules/suppliers'

const createStore = () => {
  return new Vuex.Store({
    namespaced: true,
    modules: {
      suppliers: suppliersModule
    }
  })
}

export default createStore
