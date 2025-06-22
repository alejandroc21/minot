import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';
import { HttpClientTestingModule, HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { environment } from '../../../environments/environment';
import { TokenResponse } from '../model/token-response';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;
  const API_URL = environment.env.API_URL + '/auth';

 
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers:[AuthService, provideHttpClientTesting()]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(()=>{
    httpMock.verify();
  })
  
  it('should be created', () => {
    expect(service).toBeTruthy();
  });


    it('should register a user and set the token', () => {
    const mockRequest = { name:'test user', email: 'test@example.com', password: '123456' };
    const mockToken = 'mock.jwt.token';
    const mockResponse: TokenResponse = { token: mockToken };

    service.register(mockRequest).subscribe((res) => {
      expect(res.token).toEqual(mockToken);
      expect(service.authToken).toEqual(mockToken);
      
      expect(service.currentUser()).toEqual({
        name: mockRequest.name,
        email: mockRequest.email,
      });
    });

    const req = httpMock.expectOne(`${API_URL}/register`);
    expect(req.request.method).toBe('POST');
    expect(req.request.withCredentials).toBeTrue();
    req.flush(mockResponse);
  });

});
