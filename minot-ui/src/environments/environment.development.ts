export const environment = {
    production:false,
    env:{
        API_URL: import.meta.env['NG_APP_API'],
        NG_APP_GOOGLE_CLIENT_ID: import.meta.env['NG_APP_GOOGLE_CLIENT_ID']
    }
};
