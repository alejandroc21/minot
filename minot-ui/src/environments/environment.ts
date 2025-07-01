export const environment = {
    production:true,
    env:{
        API_URL: `${import.meta.env['NG_APP_API']}/api`,
        NG_APP_GOOGLE_CLIENT_ID: import.meta.env['NG_APP_GOOGLE_CLIENT_ID']
    }
};
