package integration.ticket.latam.extractor

import integration.exception.ExtractorException
import integration.http.dto.StepResponse.StepSuccess
import integration.ticket.latam.dto.LatamTicketRequest
import integration.ticket.latam.dto.LatamTicketResponse
import integration.ticket.latam.step.GetCookiesStep
import integration.ticket.latam.step.GetTicketsStep
import shared.extension.StringExtensions.convertTo

class LatamTicketExtractor(
    private val getCookiesStep: GetCookiesStep,
    private val getTicketsStep: GetTicketsStep
) {

    fun extract(request: LatamTicketRequest): LatamTicketResponse {
        val cookies = when(val response = getCookiesStep.doRequest()){
            is StepSuccess -> response.getHeaderByKey(SET_COOKIE_KEY)
            else -> throw ExtractorException("Error when extract response from GetCookiesStep: $response")
        }

        return when(val response = getTicketsStep.doRequest(request, cookies)){
            is StepSuccess -> response.payload.convertTo()
            else -> throw ExtractorException("Error when extract response from GetTicketsStep: $response")
        }
    }

    companion object{
        const val SET_COOKIE_KEY = "Set-Cookie"
    }

}