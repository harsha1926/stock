<template>
  <v-app id="stock">
    <v-navigation-drawer v-model="drawer" fixed clipped app>
      <v-list dense class="grey lighten-4">
        <template v-for="(item, i) in items">
          <v-list-tile :key="i" :to="item.link">
            <v-list-tile-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title>{{ $t(item.text) }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
        </template>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar color="primary" app absolute clipped-left>
      <v-toolbar-side-icon @click="drawer = !drawer"></v-toolbar-side-icon>
      <v-img src="/icon.png" aspect-ratio="1" min-width="30px" max-width="40px" class="ml-3 mr-0"></v-img>

      <span class="title ml-3 mr-5">{{ $t('app.title') }}</span>
      <v-text-field solo-inverted flat hide-details label="Search" prepend-inner-icon="search"></v-text-field>
      <v-spacer></v-spacer>

      <v-menu offset-y>
        <v-tooltip slot="activator" left>
          <v-btn slot="activator" icon>
            <v-icon>language</v-icon>
          </v-btn>
          <span>{{ $t('app.toolbar.change_lang') }}</span>
        </v-tooltip>
        <v-list>
          <v-list-tile @click="$locale = 'en'">
            <v-list-tile-title>
              {{ $t('app.toolbar.english') }}
              <v-icon v-if="$locale === 'en'">check</v-icon>
            </v-list-tile-title>
          </v-list-tile>
          <v-list-tile @click="$locale = 'te'">
            <v-list-tile-title>
              {{ $t('app.toolbar.telugu') }}
              <v-icon v-if="$locale === 'te'">check</v-icon>
            </v-list-tile-title>
          </v-list-tile>
        </v-list>
      </v-menu>
    </v-toolbar>
    <nuxt />
  </v-app>
</template>

<script>
export default {
  props: {
    source: String
  },
  data: () => ({
    drawer: true,
    items: [
      { icon: 'home', text: 'app.actions.home', link: '/' },
      { icon: 'group', text: 'app.actions.suppliers', link: '/suppliers' },
      { icon: 'group', text: 'app.actions.customers', link: '/customers' },
      {
        icon: 'group',
        text: 'app.actions.products',
        link: '/products'
      },
      {
        icon: 'group',
        text: 'app.actions.supplier_transactions.label',
        link: '/supplierTransactions'
      }
    ]
  }),
  head() {
    return {
      title: this.$t('app.title')
    }
  }
}
</script>

<style lang="stylus">
#stock {
  .v-navigation-drawer__border {
    display: none;
  }
}
</style>
