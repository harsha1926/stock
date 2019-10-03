const setSearchText = (context, searchText) => {
  context.commit('SEARCH_TEXT_UPDATED', searchText)
}

export default {
  setSearchText
}
