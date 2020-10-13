import { throwError } from 'rxjs';
import { TestBed } from '@angular/core/testing';
import { AnalyticsService } from './analytics.service';

describe('AnalyticsService', () => {
  let service: AnalyticsService;

  const analyticsPageViewMock = {
    event: 'event',
    page: 'page'
  };

  const analyticsEventMock = {
    'event-action': 'event',
    'event-category': 'page',
    'event-label': 'label'
  };

  const errorMock = Error('Não foi possível enviar para o analytics: TypeError: this.getDataLayer(...).push is not a function');

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnalyticsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should push page view on dataLayer when call the pushPageView method', () => {
    // given
    jest.spyOn(service, 'getDataLayer');

    // when
    service.pushPageView(analyticsPageViewMock);

    // then
    expect(service.getDataLayer).toBeCalled();
  });

  it('should throw an exception when call the pushPageView method', () => {
    // given
    jest.spyOn(service, 'getDataLayer').mockReturnValue(throwError({ status: 404 }));

    try {
      // when
      service.pushPageView(analyticsPageViewMock);
    } catch (e) {
      // then
      expect(e).toStrictEqual(errorMock);
    }
  });

  it('should push event on dataLayer when call the pushEvent method', () => {
    // given
    jest.spyOn(service, 'getDataLayer');

    // when
    service.pushEvent(analyticsEventMock);

    // then
    expect(service.getDataLayer).toBeCalled();
  });

  it('should throw an exception when call the pushEvent method', () => {
    // given
    jest.spyOn(service, 'getDataLayer').mockReturnValue(throwError({ status: 404 }));

    try {
      // when
      service.pushEvent(analyticsEventMock);
    } catch (e) {
      // then
      expect(e).toStrictEqual(errorMock);
    }
  });

});
