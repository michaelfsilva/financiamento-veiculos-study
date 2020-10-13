export class Utils {
    public static inputCurrency(input: any): string {
        let result = input.replace(/\D/g, '');
        result = (result / 100).toFixed(2);
        result = result.replace('.', ',');
        result = result.replace(/(\d)(\d{3})(\d{3}),/g, '$1.$2.$3,');
        result = result.replace(/(\d)(\d{3}),/g, '$1.$2,');

        return result;
    }
}
