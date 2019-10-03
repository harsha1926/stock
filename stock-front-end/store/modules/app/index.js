import actions from './actions'
import getters from './getters'
import mutations from './mutations'

const state = {
  search: null
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
