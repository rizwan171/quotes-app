import { ANIME_CHAN_API_URL } from "@/constants/api_urls";
import type { GeneratedQuote } from "@/types/GeneratedQuote";
import type { QuoteGenerationOptions } from "@/types/QuoteGenerationOptions";

export const generateQuote = async (options?: QuoteGenerationOptions): Promise<GeneratedQuote | null> => {
  let requestOptionsString = "";
  if (options && options.anime) {
    requestOptionsString += `/anime?title=${options.anime}`;
  }

  const response = await fetch(ANIME_CHAN_API_URL + requestOptionsString, {
    method: "GET"
  });

  if (response.status !== 200) {
    return null;
  }

  const quote = await response.json();

  // TODO save this generated quote somewhere

  return {
    quoteText: quote.quote,
    author: quote.character,
    origin: quote.anime
  };
};
