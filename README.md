# GymFitness App
## Overview
**GymFitness** là ứng dụng di động được thiết kế để giúp người dùng quản lý hành trình rèn luyện thể chất của mình một cách hiệu quả. 
Ứng dụng cung cấp cho người dùng các bài tập luyện được cá nhân hóa, kế hoạch dinh dưỡng và theo dõi tiến trình, tất cả ở một nơi. 
Ứng dụng được xây dựng bằng **Java**, **XML** và tuân theo kiến ​​trúc **MVVM**.
###  link apk: https://www.mediafire.com/file/6gq8k96488mmofr/gym-fitness.apk/file
### Key Features
- **User Authentication**: Đăng nhập 1 lần
- **Workout Plans**: Các bài tập luyện được cá nhân hóa dựa trên sở thích và mục tiêu của người dùng.
- **Progress Tracking**: Người dùng có thể theo dõi tiến trình tập luyện và theo dõi các cột mốc rèn luyện thể chất.
- **Exercise Library**: Thư viện bài tập chi tiết có hướng dẫn, hình ảnh và video.
- **Notifications**: Người dùng nhận được lời nhắc về các bài tập luyện mỗi ngày và tổng kcal tập được một ngày.
### App Preview
# SetUp
![Untitled](https://github.com/user-attachments/assets/0e34231f-ac7d-4364-9009-b91ea7a9dfc5)
![setup2](https://github.com/user-attachments/assets/58efe24d-9807-453f-8c35-8023d1c728a9)
# Home
Thanh tiêu đề: Hiển thị lời chào, khẩu hiệu của ứng dụng, cùng với các nút chức năng như tìm kiếm, thông báo và hồ sơ cá nhân.

Thanh điều hướng chính: Cung cấp điều hướng dễ dàng giữa các chức năng chính của ứng dụng: Tập luyện, Theo dõi, Dinh dưỡng, và Cộng đồng.

Phần Khuyến nghị: Gợi ý các bài tập cá nhân hóa dựa trên mục tiêu và sở thích của người dùng.

Phần Thách thức hàng tuần: Đưa ra những thử thách tập luyện mới mỗi tuần để tạo động lực cho người dùng.

Phần Bài viết & Mẹo: Cung cấp kiến thức và mẹo về tập luyện và dinh dưỡng nhằm giúp người dùng cải thiện sức khỏe và thể hình.

Thanh điều hướng dưới cùng: Cho phép người dùng chuyển nhanh giữa các phần chính của ứng dụng.

Resource: Chứa các video hướng dẫn chi tiết về tập luyện và bài viết chuyên sâu cung cấp kiến thức về thể thao và sức khỏe.

Starts: Hiển thị danh sách các bài tập và bài viết mà người dùng đã lưu để dễ dàng truy cập lại.

Chatbot: Hỗ trợ người dùng tìm kiếm thông tin và trả lời các câu hỏi liên quan đến việc tập luyện và sức khỏe.
![home](https://github.com/user-attachments/assets/46b30e1f-6e31-4eda-ba58-6f8beadd7b2b)
# Workout 
Hiển thị các Round cho từng cái Workout và Round sẽ chứa những video cho người dùng tập.
![workout](https://github.com/user-attachments/assets/d7f644ba-15c2-48d4-9d5c-ff1affe62639)

#Ex 
Hiển thị những bài viết Article, chỉnh sửa thông tin các nhân, tạo Routine dựa trên sở thích và mục tiêu của người dùng
![3](https://github.com/user-attachments/assets/caaac6e3-619d-4b6d-8bb6-ce80b09c07ef)

### Công cụ và thư viện đã dùng
- Navigation Component: Sử dụng một Activity để chứa nhiều Fragment thay vì tạo nhiều Activity riêng lẻ, giúp quản lý điều hướng giữa các phần của ứng dụng dễ dàng.
- MVVM & LiveData: Áp dụng mô hình MVVM để tách biệt logic và giao diện người dùng, LiveData giúp theo dõi và lưu trữ trạng thái dữ liệu một cách an toàn ngay cả khi cấu hình màn hình thay đổi.
- Coroutines: Giúp thực hiện các tác vụ bất đồng bộ trên nền, đảm bảo giao diện người dùng không bị gián đoạn và cải thiện hiệu suất.
- View Binding: Tự động liên kết các view trong layout với mã nguồn, loại bỏ sự cần thiết của phương thức findViewById(), giúp mã nguồn gọn gàng và dễ bảo trì.
- Data Binding: Cho phép liên kết dữ liệu trực tiếp từ mã nguồn vào giao diện người dùng (XML), giảm thiểu việc viết mã xử lý dữ liệu giao diện, giúp ứng dụng phản hồi nhanh hơn.
- Glide: Thư viện hỗ trợ tải và lưu trữ hình ảnh, cho phép hiển thị ảnh một cách mượt mà và tối ưu nhờ vào cơ chế cache ảnh.
- Retrofit: Thư viện mạnh mẽ để thực hiện các yêu cầu HTTP, cho phép kết nối đến các API một cách dễ dàng và thuận tiện.
- Room: Thư viện của Android giúp tương tác với cơ sở dữ liệu SQLite một cách dễ dàng, hỗ trợ truy vấn, cập nhật, và lưu trữ dữ liệu cục bộ trên thiết bị.
- Lottie: Thư viện hỗ trợ việc hiển thị các file hoạt hình JSON, giúp tạo ra các hiệu ứng động mượt mà mà không cần nhiều tài nguyên.
- WorkManager: Quản lý và lên lịch các tác vụ cần thực hiện trong nền, kể cả khi ứng dụng bị đóng hoặc thiết bị khởi động lại.






