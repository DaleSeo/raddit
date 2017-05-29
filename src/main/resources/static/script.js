new Vue({
  el: '#app',
  data: {
    topics: [],
    newTopic: '',
    message: '',
    loading: false,
    inProgress: false
  },
  filters: {
    formatDate(timestamp) {
      return moment(timestamp).format('HH:mm:ss MMM Do YYYY')
    }
  },
  created () {
    this.fetch()
  },
  methods: {
    fetch () {
      this.loading = true
      this.$http.get('/topics')
        .then(response => response.data)
        .then(topics => this.topics = topics)
        .catch(window.alert.bind(window))
        .then(_ => this.loading = false)
    },
    contribute () {
      this.inProgress = true
      this.$http.post('/topics', this.newTopic)
        .then(response => response.data)
        .then(id => `Added ${id}`)
        .then(console.log.bind(console))
        .catch(window.alert.bind(window))
        .then(_ => {
          this.fetch()
          this.clearForm()
          this.inProgress = false
        })
    },
    clearForm () {
      this.newTopic = ''
    },
    vote (id, upDown) {
      this.$http.put(`/topics/${id}/${upDown}`)
        .catch(window.alert.bind(window))
        .then(this.fetch)
    }
  }
})
