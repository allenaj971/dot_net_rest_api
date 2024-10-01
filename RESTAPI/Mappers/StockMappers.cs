using Dtos.Stock;
using Models;

namespace Mappers
{
    public static class StockMappers
    {
        public static ReadStockDto ToStockDto(this Stock stockModel)
        {
            return new ReadStockDto
            {
                Id = stockModel.Id,
                Symbol = stockModel.Symbol,
                CompanyName = stockModel.CompanyName,
                Industry = stockModel.Industry,
                Purchase = stockModel.Purchase,
                LastDiv = stockModel.LastDiv,
                MarketCap = stockModel.MarketCap,
                Comments = stockModel.Comments.Select(c => c.ToCommentDto()).ToList()
            };
        }

        public static Stock ToStockFromCreateDto(this CreateStockDto csd)
        {
            return new Stock
            {
                Symbol = csd.Symbol,
                CompanyName = csd.CompanyName,
                Purchase = csd.Purchase,
                LastDiv = csd.LastDiv,
                Industry = csd.Industry,
                MarketCap = csd.MarketCap,
            };
        }
    }
}