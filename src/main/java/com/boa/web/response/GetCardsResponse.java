package com.boa.web.response;

import java.util.ArrayList;
import java.util.List;

import com.boa.web.response.cardsrequest.Card;

/**
 * GetCardsResponse
 */
public class GetCardsResponse extends GenericResponse {

	private List<Card> card = new ArrayList<>();

	public List<Card> getCard()
	{
		if(this.card==null || this.card.isEmpty()) this.card = new ArrayList<>();
		return this.card;
	}
	public void setCard(List<Card>card)
	{
		this.card = card;
	}

}