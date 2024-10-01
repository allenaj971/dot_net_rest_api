using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Dtos.Stock
{
    public class UpdateStockDto
    {
        [Required]
        [MaxLength(10, ErrorMessage = "Title character limit of 10")]
        public string Symbol { get; set; } = string.Empty;
        [Required]
        [MaxLength(300, ErrorMessage = "Title character limit of 300")]
        public string CompanyName { get; set; } = string.Empty;
        [Required]
        public decimal Purchase { get; set; }
        [Required]
        public decimal LastDiv { get; set; }
        [Required]
        [MaxLength(300, ErrorMessage = "Title character limit of 300")]
        public string Industry { get; set; } = string.Empty;
        [Required]
        public long MarketCap { get; set; }
    }
}