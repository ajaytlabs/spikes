import All4 from './all4.js'

exports.webhook = async (event, context, callback) => {
  const requestBody = JSON.parse(event.body).result
  const result = await new All4().fetch(requestBody)
  callback(null, {statusCode: 200, headers: {}, body: JSON.stringify(result)})
}
