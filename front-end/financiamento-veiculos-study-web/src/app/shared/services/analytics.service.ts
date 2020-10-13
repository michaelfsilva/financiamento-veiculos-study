import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AnalyticsPageView } from '@models/analytics-pageview.model';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {
  static DATALAYER = environment.googleTagManager.data;

  constructor() { }

  getDataLayer(): any {
    if (!window[AnalyticsService.DATALAYER]) {
      window[AnalyticsService.DATALAYER] = [];
    }
    return window[AnalyticsService.DATALAYER];
  }

  pushPageView(analyticsPageView: AnalyticsPageView, mantemAcentuacao = false): void {
    try {
      analyticsPageView.event = this.removeAccentuationIfNecessary(analyticsPageView.event, mantemAcentuacao);
      analyticsPageView.page = this.removeAccentuationIfNecessary(analyticsPageView.page, mantemAcentuacao);

      this.getDataLayer().push(analyticsPageView);
    } catch (e) {
      throw new Error(`Não foi possível enviar para o analytics: ${e}`);
    }
  }

  pushEvent(analyticsEvent: any, mantemAcentuacao = false): void {
    try {
      analyticsEvent.event = this.removeAccentuationIfNecessary(analyticsEvent.event, mantemAcentuacao);
      analyticsEvent['event-action'] = this.removeAccentuationIfNecessary(analyticsEvent['event-action'], mantemAcentuacao);
      analyticsEvent['event-category'] = this.removeAccentuationIfNecessary(analyticsEvent['event-category'], mantemAcentuacao);
      analyticsEvent['event-label'] = this.removeAccentuationIfNecessary(analyticsEvent['event-label'], mantemAcentuacao);

      this.getDataLayer().push(analyticsEvent);
    } catch (e) {
      throw new Error(`Não foi possível enviar para o analytics: ${e}`);
    }
  }

  private removeAccentuationIfNecessary(value: string, mantemAcentuacao: boolean): string {
    let substitutedValue = null;
    if (value && !mantemAcentuacao) {
      substitutedValue = value.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
      return substitutedValue;
    }
  }
}
