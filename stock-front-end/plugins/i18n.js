import Vue from 'vue'
import VueI18n from 'vue-i18n'

Vue.use(VueI18n)

export default ({ app }) => {
  app.i18n = new VueI18n({
    locale: 'en',
    fallbackLocale: 'en',
    silentTranslationWarn: true,
    messages: {
      en: require('~/locales/en.json'),
      te: require('~/locales/te.json')
    }
  })

  Object.defineProperty(Vue.prototype, '$locale', {
    get: function() {
      return app.i18n.locale
    },
    set: function(locale) {
      app.i18n.locale = locale
    }
  })

  Vue.filter('translate', function(value) {
    return app.i18n.t(value)
  })
}
