import actions from './actions'
import getters from './getters'
import mutations from './mutations'

const state = {
  stockParcels: []
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
