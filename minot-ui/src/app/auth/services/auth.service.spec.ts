import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../../../environments/environment';
import { TokenResponse } from '../model/token-response';

describe('AuthService', () => {
  let service: AuthService;
  let httpTestingController: HttpTestingController;
  const API_URL = environment.env.API_URL + '/auth';

  const mockAccessToken =
    'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30';
  const mockDecodeUser = {
    name: 'test user',
    email: 'test@test.com',
  };

  const mockTokenResponse: TokenResponse = {
    token: mockAccessToken,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: []
    })

    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
