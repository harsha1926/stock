import actions from './actions'
import getters from './getters'
import mutations from './mutations'

const state = {
  customers: []
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
