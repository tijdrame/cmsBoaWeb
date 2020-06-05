package com.boa.web.response;

import com.boa.web.response.cardsrequest.CardDetails;

/**
 * GetCardsResponse
 */
public class GetCardsDetailResponse extends GenericResponse {

	private CardDetails card;

	public CardDetails getCard()
	{
		return this.card;
	}
	public void setCard(CardDetails card)
	{
		this.card = card;
	}

}