package com.marketboro.point.gcp;

//@Service
//@Slf4j
//@RequiredArgsConstructor
public class GCSService {

//    private final Storage storage;
//
////    public java.sql.Blob downloadFileFromGCS() {
////        java.sql.Blob blob = storage.get("버켓이름", "버킷에서 다운로드할 파일 이름");
////        blob.downloadTo(Paths.get("로컬에 저장할 파일 이름"));
////        return blob;
////    }
//    public Blob downloadFileFromGCS(DownloadReqDto downloadReqDto) {
//
//        String bucketName = downloadReqDto.getBucketName();
//        String downloadFileName = downloadReqDto.getDownloadFileName();
//        String localFileLocation = downloadReqDto.getLocalFileLocation();
//
//        Blob blob = storage.get(bucketName, downloadFileName);
//        log.info("localFileLocation : {}", localFileLocation);
//        Path path = Paths.get(localFileLocation);
//        log.info("Paths.get(localFileLocation) : {}", path);
//        blob.downloadTo(path);
////        blob.downloadTo(Path.of(localFileLocation));
//
//        return blob;
//    }
//
//
//    @SuppressWarnings("deprecation")
//    public BlobInfo uploadFileToGCS(UploadReqDto uploadReqDto) throws IOException {
//
//        BlobInfo blobInfo =storage.create(
//                BlobInfo.newBuilder(uploadReqDto.getBucketName(), uploadReqDto.getUploadFileName())
//                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
//                        .build(),
//                new FileInputStream(uploadReqDto.getLocalFileLocation()));
//
//        return blobInfo;
//    }
}