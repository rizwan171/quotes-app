import { ANIME_CHAN_API_URL } from "@/constants/api_urls";
import type { GeneratedQuote } from "@/types/GeneratedQuote";
import type { QuoteGenerationOptions } from "@/types/QuoteGenerationOptions";
import { saveQuote } from "./QuoteService";
import type { Quote } from "@/types/Quote";
import { CreationType } from "@/enums/CreationType";

export const generateQuote = async (options?: QuoteGenerationOptions): Promise<Quote | null> => {
  let requestOptionsString = "";
  if (options && options.anime) {
    requestOptionsString += `/anime?title=${options.anime}`;
  }

  const response = await fetch(ANIME_CHAN_API_URL + requestOptionsString, {
    method: "GET"
  });

  if (response.status !== 200) {
    // TODO handle any errors from API
    return null;
  }

  const generatedQuote: GeneratedQuote = await response.json();

  // save generated quote
  const quote: Quote = {
    quoteText: generatedQuote.quote,
    author: generatedQuote.character,
    origin: generatedQuote.anime,
    creationType: CreationType.GENERATED
  };

  try {
    return await saveQuote(quote);
  } catch (error) {
    // do nothing, this error is handled later
  }

  return quote;
};
