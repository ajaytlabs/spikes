import request from 'request-promise-native'

export default class All4 {

  async fetch(result) {
    console.log(result)
    if (result.action === 'tv_channel.now') {
      const zxc = await this.token()
      const program = await this.now(zxc, result.parameters)
      return {
        speech: program.title,
        displayText: program.title
      }
    }
  }

  async token() {
    const payload = await request({
      method: 'POST',
      uri: 'https://api.dev.channel4.com/online/v1/oauth/token',
      headers: {
        'Authorization': 'Basic YWRBS2J6dUIxT3dTS1NCV2YwbVRoOU1hOUtZT2hYTUE6UnNGR2xrWUZDZ2FrR2RWTg=='
      },
      form: {
        grant_type: 'client_credentials'
      },
      json: true
    })
    return payload.access_token
  }

  async now(token, parameters) {
    const programmesOnAllChannel = await request({
      uri: 'https://api.dev.channel4.com/online/v1/programmes/tx-schedule/now-next.json?client=android',
      headers: {
        'Authorization': `Bearer ${token}`
      },
      json: true
    })
    const time = parameters['date-time'] || 'now'
    const programmesOnDesiredChannel = programmesOnAllChannel.filter(item => item.type === time.toUpperCase())[0]
    return programmesOnDesiredChannel.sliceItems.filter(item => item.slot.slotTXChannel === parameters['tv-channel'])[0]
  }

}
