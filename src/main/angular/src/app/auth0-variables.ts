interface AuthConfig {
  CLIENT_ID: string;
  CLIENT_DOMAIN: string;
  AUDIENCE: string;
  REDIRECT: string;
  SCOPE: string;
}

export const AUTH_CONFIG: AuthConfig = {
  CLIENT_ID: '0669ac311d75d255a8c24f512f8d36eb5f583d48',
  CLIENT_DOMAIN: 'github.com',
  AUDIENCE: 'https://github.com/BoomBoOoom/github',
  REDIRECT: 'http://localhost:4200/',
  SCOPE: 'openid'
};
