import { SERVER_API_URL } from "@/constants/api_urls";
import type { Quote } from "@/types/Quote";

export const getAllUserSavedQuotes = async (): Promise<Quote[] | null> => {
  const response = await fetch(SERVER_API_URL, {
    method: "GET",
    headers: {
      "Access-Control-Allow-Origin": "*"
    }
  });

  if (response.status !== 200) {
    return null;
  }

  const quotes: Quote[] = await response.json();
  return quotes;
};

export const saveQuote = async (quote: Quote): Promise<Quote | null> => {
  const response = await fetch(SERVER_API_URL, {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    },
    body: JSON.stringify(quote)
  });

  if (!response.ok) {
    if (response.status == 400) {
      // TODO handle bad request
    }

    return null;
  }

  // TODO the response returned is empty, which causes an error. decide whether to return the saved entity or just the id, or keep the backend as is and update here
  const savedQuote: Quote = await response.json();
  return savedQuote;
};
